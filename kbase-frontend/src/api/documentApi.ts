import api from "./axios";
import type { DocumentRecord } from "../types/models";

export async function getDocuments(projectId: string): Promise<DocumentRecord[]> {
  const { data } = await api.get<DocumentRecord[]>(`/projects/${projectId}/documents`);
  return data;
}

export async function uploadDocument(projectId: number, file: File): Promise<DocumentRecord> {
  const formData = new FormData();
  formData.append("file", file);
  const { data } = await api.post<DocumentRecord>(`/projects/${projectId}/documents/upload`, formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
  return data;
}

export async function deleteDocument(documentId: number): Promise<void> {
  await api.delete(`/projects/documents/${documentId}`);
}

export async function downloadDocument(documentId: number, fallbackName: string): Promise<void> {
  const response = await api.get<Blob>(`/projects/documents/${documentId}/download`, { responseType: "blob" });
  const disposition = response.headers["content-disposition"] as string | undefined;
  const serverName = disposition?.match(/filename="?([^";]+)"?/i)?.[1];
  const url = URL.createObjectURL(response.data);
  const anchor = document.createElement("a");
  anchor.href = url;
  anchor.download = serverName || fallbackName;
  document.body.appendChild(anchor);
  anchor.click();
  anchor.remove();
  URL.revokeObjectURL(url);
}
