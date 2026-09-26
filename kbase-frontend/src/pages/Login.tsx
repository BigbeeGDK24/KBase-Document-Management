import { useState, type FormEvent } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { login as loginRequest } from "../api/authApi";
import { useAuth } from "../hooks/useAuth";
import { getApiError } from "../utils/apiError";

function Login() {
  const { isAuthenticated, login } = useAuth();
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  if (isAuthenticated) return <Navigate to="/dashboard" replace />;

  const submit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!username.trim() || !password) {
      setError("Enter your username and password.");
      return;
    }

    setError(null);
    setIsSubmitting(true);
    try {
      const { token } = await loginRequest({ username: username.trim(), password });
      login(token);
      navigate("/dashboard", { replace: true });
    } catch (loginError) {
      setError(getApiError(loginError, "Unable to sign in. Check your credentials and try again."));
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="auth-page">
      <section className="auth-intro" aria-hidden="true">
        <div className="intro-orb orb-one" /><div className="intro-orb orb-two" />
        <div className="intro-copy"><span className="brand-mark">K</span><p className="eyebrow">Knowledge, connected</p><h1>A home for every project document.</h1><p>Keep files, context, and the people who need them together.</p></div>
      </section>
      <section className="auth-panel">
        <div className="auth-card">
          <div className="auth-mobile-brand"><span className="brand-mark">K</span><strong>KBase</strong></div>
          <p className="eyebrow">Welcome back</p><h1>Sign in to KBase</h1><p className="auth-description">Access your projects and shared documents.</p>
          <form className="auth-form" onSubmit={submit}>
            <label htmlFor="login-username">Username</label>
            <input id="login-username" value={username} onChange={(event) => setUsername(event.target.value)} autoComplete="username" disabled={isSubmitting} />
            <label htmlFor="login-password">Password</label>
            <input id="login-password" type="password" value={password} onChange={(event) => setPassword(event.target.value)} autoComplete="current-password" disabled={isSubmitting} />
            {error && <p className="form-error" role="alert">{error}</p>}
            <button className="button button-primary auth-submit" type="submit" disabled={isSubmitting}>{isSubmitting ? "Signing in…" : "Sign in"}</button>
          </form>
          <p className="auth-alternate">New to KBase? <Link to="/register">Create an account</Link></p>
        </div>
      </section>
    </div>
  );
}

export default Login;
