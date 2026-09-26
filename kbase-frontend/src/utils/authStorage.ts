import type { User, UserRole } from "../types/models";

const TOKEN_KEY = "token";

interface JwtPayload {
  sub?: string;
  role?: UserRole;
  exp?: number;
}

export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY);
}

export function saveToken(token: string): void {
  localStorage.setItem(TOKEN_KEY, token);
}

export function clearToken(): void {
  localStorage.removeItem(TOKEN_KEY);
}

export function getCurrentUser(): Pick<User, "username" | "role"> | null {
  const token = getToken();
  if (!token) return null;
  try {
    const encodedPayload = token.split(".")[1];
    if (!encodedPayload) return null;
    const normalizedPayload = encodedPayload.replace(/-/g, "+").replace(/_/g, "/").padEnd(Math.ceil(encodedPayload.length / 4) * 4, "=");
    const payload = JSON.parse(atob(normalizedPayload)) as JwtPayload;
    if (!payload.sub || !payload.role) return null;
    return { username: payload.sub, role: payload.role };
  } catch {
    return null;
  }
}
