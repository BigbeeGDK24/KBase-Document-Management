import { useEffect, useRef, type PropsWithChildren } from "react";

interface ModalProps extends PropsWithChildren {
  title: string;
  onClose: () => void;
}

function Modal({ title, onClose, children }: ModalProps) {
  const dialogRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    dialogRef.current?.focus();
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === "Escape") onClose();
    };
    document.addEventListener("keydown", handleKeyDown);
    return () => document.removeEventListener("keydown", handleKeyDown);
  }, [onClose]);

  return <div className="modal-backdrop" role="presentation"><div className="modal" ref={dialogRef} role="dialog" aria-modal="true" aria-labelledby="modal-title" tabIndex={-1}><div className="modal-header"><h2 id="modal-title">{title}</h2><button className="icon-button" type="button" onClick={onClose} aria-label="Close dialog">×</button></div>{children}</div></div>;
}

export default Modal;
