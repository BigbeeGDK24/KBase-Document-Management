import { useState } from "react";
import { deleteDocument, downloadDocument } from "../api/documentApi";
import type { DocumentRecord } from "../types/models";
import { getApiError } from "../utils/apiError";
import { formatDate, formatFileSize } from "../utils/format";

interface DocumentListProps {
  documents: DocumentRecord[];
  onDeleted: (documentId: number) => void;
}

function DocumentList({ documents, onDeleted }: DocumentListProps) {
  const [error, setError] = useState<string | null>(null);
  const [deletingId, setDeletingId] = useState<number | null>(null);
  const [downloadingId, setDownloadingId] = useState<number | null>(null);

  const handleDownload = async (document: DocumentRecord) => {
    setError(null);
    setDownloadingId(document.id);
    try {
      await downloadDocument(document.id, document.fileName);
    } catch (downloadError) {
      setError(getApiError(downloadError, "Unable to download this file."));
    } finally {
      setDownloadingId(null);
    }
  };

  const handleDelete = async (document: DocumentRecord) => {
    if (!window.confirm(`Delete “${document.fileName}”? This cannot be undone.`)) return;

    setError(null);
    setDeletingId(document.id);
    try {
      await deleteDocument(document.id);
      onDeleted(document.id);
    } catch (deleteError) {
      setError(getApiError(deleteError, "Unable to delete this file."));
    } finally {
      setDeletingId(null);
    }
  };

  if (documents.length === 0) return <p className="empty-copy">No documents have been uploaded yet.</p>;

  return <>
    {error && <p className="inline-error" role="alert">{error}</p>}
    <ul className="document-list">
      {documents.map((document) => <li key={document.id}>
        <span className="document-icon" aria-hidden="true">{document.fileName.split(".").pop()?.slice(0, 3).toUpperCase() || "FILE"}</span>
        <span className="document-details"><strong>{document.fileName}</strong><small>{formatFileSize(document.fileSize)} · Uploaded {formatDate(document.uploadedAt)}{document.uploadedBy ? ` by ${document.uploadedBy.username}` : ""}</small></span>
        <span className="document-actions"><button className="text-button" type="button" onClick={() => handleDownload(document)} disabled={downloadingId === document.id}>{downloadingId === document.id ? "Downloading…" : "Download"}</button><button className="text-button danger" type="button" onClick={() => handleDelete(document)} disabled={deletingId === document.id}>{deletingId === document.id ? "Deleting…" : "Delete"}</button></span>
      </li>)}
    </ul>
  </>;
}

export default DocumentList;
