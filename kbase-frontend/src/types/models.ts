export type UserRole = "ADMIN" | "USER";
export type ProjectMemberRole = "OWNER" | "MEMBER";

export interface User {
  id: number;
  username: string;
  role: UserRole;
}

export interface ProjectMember {
  id: number;
  user: User;
  role: ProjectMemberRole;
}

export interface DocumentRecord {
  id: number;
  fileName: string;
  fileType: string;
  fileSize: number;
  uploadedAt: string;
  uploadedBy?: User;
}

export interface Project {
  id: number;
  name: string;
  description?: string | null;
  owner?: User;
  members?: ProjectMember[];
  documents?: DocumentRecord[];
  createdAt?: string;
}

export interface LoginCredentials {
  username: string;
  password: string;
}

export type RegisterCredentials = LoginCredentials;

export interface CreateProjectPayload {
  name: string;
  description: string;
}

export interface InviteMemberPayload {
  username: string;
  role: "MEMBER";
}
