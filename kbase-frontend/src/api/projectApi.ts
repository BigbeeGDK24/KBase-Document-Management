import api from "./axios";
import type { CreateProjectPayload, InviteMemberPayload, Project, ProjectMember } from "../types/models";

export async function getProjects(): Promise<Project[]> {
  const { data } = await api.get<Project[]>("/projects");
  return data;
}

export async function getProject(projectId: string): Promise<Project> {
  const { data } = await api.get<Project>(`/projects/${projectId}`);
  return data;
}

export async function createProject(payload: CreateProjectPayload): Promise<Project> {
  const { data } = await api.post<Project>("/projects", payload);
  return data;
}

export async function inviteMember(projectId: number, payload: InviteMemberPayload): Promise<ProjectMember> {
  const { data } = await api.post<ProjectMember>(`/projects/${projectId}/members`, payload);
  return data;
}
