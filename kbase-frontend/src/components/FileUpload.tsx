import { useRef, useState, type ChangeEvent } from "react";
import { uploadDocument } from "../api/documentApi";
import type { DocumentRecord } from "../types/models";
import { getApiError } from "../utils/apiError";

const acceptedExtensions = ".pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt,.md,.markdown,.jpg,.jpeg,.png,.gif,.svg,.bmp,.mp4,.mov,.avi";

interface FileUploadProps {
  projectId: number;
  onUploaded: (document: DocumentRecord) => void;
}

function FileUpload({ projectId, onUploaded }: FileUploadProps) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [isUploading, setIsUploading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleFile = async (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (!file) return;

    setError(null);
    setIsUploading(true);
    try {
      const uploadedDocument = await uploadDocument(projectId, file);
      onUploaded(uploadedDocument);
      event.target.value = "";
    } catch (uploadError) {
      setError(getApiError(uploadError, "Unable to upload this file."));
    } finally {
      setIsUploading(false);
    }
  };

  return (
    <div className="file-upload">
      <input ref={inputRef} id="document-upload" className="sr-only" type="file" accept={acceptedExtensions} onChange={handleFile} disabled={isUploading} />
      <button className="upload-dropzone" type="button" onClick={() => inputRef.current?.click()} disabled={isUploading}>
        <span className="upload-icon" aria-hidden="true">↑</span>
        <span><strong>{isUploading ? "Uploading file…" : "Upload a document"}</strong><small>PDF, Office files, images, video, text or Markdown</small></span>
      </button>
      {error && <p className="inline-error" role="alert">{error}</p>}
    </div>
  );
}

export default FileUpload;
