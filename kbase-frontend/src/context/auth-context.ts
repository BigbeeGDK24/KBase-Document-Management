import { createContext } from "react";
import type { User } from "../types/models";

export interface AuthContextValue {
  user: Pick<User, "username" | "role"> | null;
  isAuthenticated: boolean;
  login: (token: string) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextValue | undefined>(undefined);
