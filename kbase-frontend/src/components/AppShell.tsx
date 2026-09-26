import { NavLink, Outlet } from "react-router-dom";
import Navbar from "./Navbar";

function AppShell() {
  return (
    <div className="app-shell">
      <aside className="app-sidebar">
        <NavLink className="brand" to="/dashboard" aria-label="KBase projects dashboard"><span className="brand-mark">K</span><span>KBase</span></NavLink>
        <nav className="sidebar-nav" aria-label="Main navigation">
          <p className="nav-label">Workspace</p>
          <NavLink to="/dashboard" className={({ isActive }) => `sidebar-link${isActive ? " active" : ""}`} end><span aria-hidden="true">▦</span>Projects</NavLink>
        </nav>
        <p className="sidebar-footnote">Your shared knowledge,<br />organized in one place.</p>
      </aside>
      <div className="app-content"><Navbar /><main className="page-content"><Outlet /></main></div>
    </div>
  );
}

export default AppShell;
