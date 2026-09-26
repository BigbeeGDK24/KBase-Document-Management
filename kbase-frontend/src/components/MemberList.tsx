import { useState, type FormEvent } from "react";
import { inviteMember } from "../api/projectApi";
import type { ProjectMember } from "../types/models";
import { getApiError } from "../utils/apiError";
import Modal from "./Modal";

interface MemberListProps {
  projectId: number;
  members: ProjectMember[];
  canInvite: boolean;
  onMemberInvited: (member: ProjectMember) => void;
}

function MemberList({ projectId, members, canInvite, onMemberInvited }: MemberListProps) {
  const [isInviteOpen, setIsInviteOpen] = useState(false);
  const [username, setUsername] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const submitInvite = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!username.trim()) {
      setError("Enter a username to invite.");
      return;
    }

    setError(null);
    setIsSubmitting(true);
    try {
      const member = await inviteMember(projectId, { username: username.trim(), role: "MEMBER" });
      onMemberInvited(member);
      setUsername("");
      setIsInviteOpen(false);
    } catch (inviteError) {
      setError(getApiError(inviteError, "Unable to invite this member."));
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <>
      <div className="section-heading">
        <div><h2>Members</h2><p>People with access to this project.</p></div>
        {canInvite && <button className="button button-secondary" type="button" onClick={() => setIsInviteOpen(true)}>Invite member</button>}
      </div>
      {members.length === 0 ? (
        <p className="empty-copy">No project members yet.</p>
      ) : (
        <ul className="member-list">
          {members.map((member) => <li key={member.id}>
            <span className="member-avatar" aria-hidden="true">{member.user.username.slice(0, 1).toUpperCase()}</span>
            <span><strong>{member.user.username}</strong><small>{member.user.role}</small></span>
            <span className={`role-pill role-${member.role.toLowerCase()}`}>{member.role}</span>
          </li>)}
        </ul>
      )}
      {isInviteOpen && <Modal title="Invite a member" onClose={() => { if (!isSubmitting) setIsInviteOpen(false); }}>
        <form className="modal-form" onSubmit={submitInvite}>
          <p className="modal-description">Invite an existing KBase user as a project member.</p>
          <label htmlFor="member-username">Username</label>
          <input id="member-username" value={username} onChange={(event) => setUsername(event.target.value)} autoComplete="username" autoFocus />
          {error && <p className="inline-error" role="alert">{error}</p>}
          <div className="modal-actions"><button className="button button-ghost" type="button" disabled={isSubmitting} onClick={() => setIsInviteOpen(false)}>Cancel</button><button className="button button-primary" type="submit" disabled={isSubmitting}>{isSubmitting ? "Inviting…" : "Invite member"}</button></div>
        </form>
      </Modal>}
    </>
  );
}

export default MemberList;
