import { Link } from "react-router-dom";
import type { Project } from "../types/models";
import { formatDate } from "../utils/format";

interface ProjectCardProps {
  project: Project;
}

function ProjectCard({ project }: ProjectCardProps) {
  const memberCount = project.members?.length ?? 0;

  return (
    <article className="project-card">
      <div className="project-card-icon" aria-hidden="true">P</div>
      <div className="project-card-copy">
        <p className="eyebrow">Project</p>
        <h2>{project.name}</h2>
        <p className="project-description">{project.description || "No description has been added yet."}</p>
      </div>
      <dl className="project-meta">
        <div><dt>Owner</dt><dd>{project.owner?.username || "Not available"}</dd></div>
        <div><dt>Members</dt><dd>{memberCount}</dd></div>
        <div><dt>Created</dt><dd>{formatDate(project.createdAt)}</dd></div>
      </dl>
      <Link className="button button-secondary project-card-action" to={`/projects/${project.id}`}>Open project</Link>
    </article>
  );
}

export default ProjectCard;
