import { useCallback, useEffect, useState, type FormEvent } from "react";
import { createProject, getProjects } from "../api/projectApi";
import ProjectCard from "../components/ProjectCard";
import Modal from "../components/Modal";
import type { Project } from "../types/models";
import { getApiError } from "../utils/apiError";

function Dashboard() {
  const [projects, setProjects] = useState<Project[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [loadError, setLoadError] = useState<string | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [formError, setFormError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const loadProjects = useCallback(async () => {
    setIsLoading(true);
    setLoadError(null);
    try {
      setProjects(await getProjects());
    } catch (error) {
      setLoadError(getApiError(error, "Unable to load projects."));
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => { void loadProjects(); }, [loadProjects]);

  const closeModal = () => {
    if (isSubmitting) return;
    setIsModalOpen(false);
    setFormError(null);
  };

  const submitProject = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!name.trim()) {
      setFormError("Project name is required.");
      return;
    }

    setFormError(null);
    setIsSubmitting(true);
    try {
      const project = await createProject({ name: name.trim(), description: description.trim() });
      setProjects((current) => [project, ...current]);
      setName("");
      setDescription("");
      setIsModalOpen(false);
    } catch (error) {
      setFormError(getApiError(error, "Unable to create this project."));
    } finally {
      setIsSubmitting(false);
    }
  };

  return <>
    <section className="page-heading">
      <div><p className="eyebrow">Workspace</p><h2>Your projects</h2><p>Manage the information, people, and documents behind your work.</p></div>
      <button className="button button-primary" type="button" onClick={() => setIsModalOpen(true)}>+ Create project</button>
    </section>
    {isLoading ? <div className="project-grid" aria-label="Loading projects"><div className="skeleton-card" /><div className="skeleton-card" /><div className="skeleton-card" /></div> : loadError ? <section className="feedback-panel" role="alert"><h2>Couldn’t load projects</h2><p>{loadError}</p><button className="button button-secondary" type="button" onClick={() => void loadProjects()}>Try again</button></section> : projects.length === 0 ? <section className="feedback-panel"><span className="empty-icon" aria-hidden="true">+</span><h2>Create your first project</h2><p>Projects keep documents and collaborators in one focused workspace.</p><button className="button button-primary" type="button" onClick={() => setIsModalOpen(true)}>Create project</button></section> : <div className="project-grid">{projects.map((project) => <ProjectCard key={project.id} project={project} />)}</div>}
    {isModalOpen && <Modal title="Create a project" onClose={closeModal}>
      <form className="modal-form" onSubmit={submitProject}>
        <p className="modal-description">Start a shared space for a project and its supporting documents.</p>
        <label htmlFor="project-name">Project name</label><input id="project-name" value={name} onChange={(event) => setName(event.target.value)} autoFocus disabled={isSubmitting} />
        <label htmlFor="project-description">Description <span className="optional">Optional</span></label><textarea id="project-description" value={description} onChange={(event) => setDescription(event.target.value)} rows={4} disabled={isSubmitting} />
        {formError && <p className="inline-error" role="alert">{formError}</p>}
        <div className="modal-actions"><button className="button button-ghost" type="button" disabled={isSubmitting} onClick={closeModal}>Cancel</button><button className="button button-primary" type="submit" disabled={isSubmitting}>{isSubmitting ? "Creating…" : "Create project"}</button></div>
      </form>
    </Modal>}
  </>;
}

export default Dashboard;
