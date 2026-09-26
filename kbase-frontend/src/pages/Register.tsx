import { useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../api/authApi";
import { getApiError } from "../utils/apiError";

function Register() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const submit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!username.trim() || !password) {
      setError("Enter a username and password.");
      return;
    }

    setError(null);
    setIsSubmitting(true);
    try {
      await register({ username: username.trim(), password });
      navigate("/login", { replace: true });
    } catch (registerError) {
      setError(getApiError(registerError, "Unable to create the account. Please try again."));
    } finally {
      setIsSubmitting(false);
    }
  };

  return <div className="simple-auth-page"><div className="auth-card">
    <Link className="simple-brand" to="/login"><span className="brand-mark">K</span><strong>KBase</strong></Link>
    <p className="eyebrow">Get started</p><h1>Create your account</h1><p className="auth-description">Join KBase to work together on project knowledge.</p>
    <form className="auth-form" onSubmit={submit}>
      <label htmlFor="register-username">Username</label><input id="register-username" value={username} onChange={(event) => setUsername(event.target.value)} autoComplete="username" disabled={isSubmitting} />
      <label htmlFor="register-password">Password</label><input id="register-password" type="password" value={password} onChange={(event) => setPassword(event.target.value)} autoComplete="new-password" disabled={isSubmitting} />
      {error && <p className="form-error" role="alert">{error}</p>}
      <button className="button button-primary auth-submit" type="submit" disabled={isSubmitting}>{isSubmitting ? "Creating account…" : "Create account"}</button>
    </form>
    <p className="auth-alternate">Already have an account? <Link to="/login">Sign in</Link></p>
  </div></div>;
}

export default Register;
