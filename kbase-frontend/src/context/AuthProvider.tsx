import { useMemo, useState, type PropsWithChildren } from "react";
import { AuthContext } from "./auth-context";
import { clearToken, getCurrentUser, getToken, saveToken } from "../utils/authStorage";

function AuthProvider({ children }: PropsWithChildren) {
  const [user, setUser] = useState(() => (getToken() ? getCurrentUser() : null));

  const value = useMemo(() => ({
    user,
    isAuthenticated: Boolean(user),
    login: (token: string) => {
      saveToken(token);
      setUser(getCurrentUser());
    },
    logout: () => {
      clearToken();
      setUser(null);
    },
  }), [user]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export default AuthProvider;
