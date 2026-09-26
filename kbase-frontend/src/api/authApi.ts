import api from "./axios";
import type { LoginCredentials, RegisterCredentials } from "../types/models";

interface LoginResponse {
  token: string;
}

export async function login(credentials: LoginCredentials): Promise<LoginResponse> {
  const { data } = await api.post<LoginResponse>("/auth/login", credentials);
  return data;
}

export async function register(credentials: RegisterCredentials): Promise<string> {
  const { data } = await api.post<string>("/auth/register", credentials);
  return data;
}
