import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

const pageTitles: Record<string, string> = {
  "/dashboard": "Projects",
};

function Navbar() {
  const { user, logout } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();
  const title = pageTitles[location.pathname] || "Project workspace";

  const signOut = () => {
    logout();
    navigate("/login");
  };

  return <header className="navbar"><div><p className="navbar-eyebrow">KBase</p><h1>{title}</h1></div><div className="navbar-user"><Link className="user-chip" to="/dashboard"><span>{user?.username.slice(0, 1).toUpperCase()}</span><strong>{user?.username}</strong><em>{user?.role}</em></Link><button className="button button-ghost" type="button" onClick={signOut}>Logout</button></div></header>;
}

export default Navbar;
