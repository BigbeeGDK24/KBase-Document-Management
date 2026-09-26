import { useCallback, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { getDocuments } from "../api/documentApi";
import { getProject } from "../api/projectApi";
import DocumentList from "../components/DocumentList";
import FileUpload from "../components/FileUpload";
import MemberList from "../components/MemberList";
import { useAuth } from "../hooks/useAuth";
import type { DocumentRecord, Project, ProjectMember } from "../types/models";
import { getApiError } from "../utils/apiError";
import { formatDate } from "../utils/format";

function ProjectDetail() {
  const { id } = useParams();
  const { user } = useAuth();
  const [project, setProject] = useState<Project | null>(null);
  const [documents, setDocuments] = useState<DocumentRecord[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const loadProject = useCallback(async () => {
    if (!id) return;
    setIsLoading(true);
    setError(null);
    try {
      const [projectResponse, documentsResponse] = await Promise.all([getProject(id), getDocuments(id)]);
      setProject(projectResponse);
      setDocuments(documentsResponse);
    } catch (requestError) {
      setError(getApiError(requestError, "Unable to load this project."));
    } finally {
      setIsLoading(false);
    }
  }, [id]);

  useEffect(() => { void loadProject(); }, [loadProject]);

  if (!id) return <section className="feedback-panel" role="alert"><h2>Invalid project</h2><Link className="button button-secondary" to="/dashboard">Back to projects</Link></section>;
  if (isLoading) return <div className="detail-loading"><div className="skeleton-line wide" /><div className="skeleton-panel" /><div className="skeleton-panel" /></div>;
  if (error || !project) return <section className="feedback-panel" role="alert"><h2>Couldn’t load this project</h2><p>{error || "The project could not be found."}</p><div className="feedback-actions"><button className="button button-secondary" type="button" onClick={() => void loadProject()}>Try again</button><Link className="button button-ghost" to="/dashboard">Back to projects</Link></div></section>;

  const members = project.members || [];
  const canInvite = project.owner?.username === user?.username;
  const onMemberInvited = (member: ProjectMember) => setProject((current) => current ? { ...current, members: [...(current.members || []), member] } : current);
  const onUploaded = (document: DocumentRecord) => setDocuments((current) => [document, ...current]);
  const onDeleted = (documentId: number) => setDocuments((current) => current.filter((document) => document.id !== documentId));

  return <>
    <Link className="back-link" to="/dashboard">← All projects</Link>
    <section className="project-hero"><div><p className="eyebrow">Project workspace</p><h2>{project.name}</h2><p>{project.description || "No description has been added to this project."}</p></div><dl><div><dt>Owner</dt><dd>{project.owner?.username || "Not available"}</dd></div><div><dt>Created</dt><dd>{formatDate(project.createdAt)}</dd></div></dl></section>
    <div className="detail-grid">
      <section className="content-panel members-panel"><MemberList projectId={project.id} members={members} canInvite={canInvite} onMemberInvited={onMemberInvited} /></section>
      <section className="content-panel documents-panel"><div className="section-heading"><div><h2>Documents</h2><p>Upload, download, and maintain your project files.</p></div></div><FileUpload projectId={project.id} onUploaded={onUploaded} /><DocumentList documents={documents} onDeleted={onDeleted} /></section>
    </div>
  </>;
}

export default ProjectDetail;
