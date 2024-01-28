import React, {ReactNode} from 'react';

interface ModalProps {
    show : boolean,
    onClose: () => void;
    children : ReactNode
}
const Modal = (props : ModalProps) => {
    console.log ("HERE")
    console.log (props.show)
    console.log (props.children)
    if (!props.show) return null;

    const modalStyle : React.CSSProperties = {
        display: 'block',
        position: 'fixed',
        zIndex: 9999,
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        textAlign: 'center',
    };

    const modalContentStyle : React.CSSProperties= {
        backgroundColor: '#fff',
        margin: 'auto',
        padding: '20px',
        width: '50%',
        borderRadius: '8px',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.3)',
        position: 'relative',
        top: '50%',
        transform: 'translateY(-50%)',
    };

    return (
        <div style={modalStyle} onClick={props.onClose}>
            <div style={modalContentStyle} onClick={(e) => e.stopPropagation()}>
                {props.children}
            </div>
        </div>
    );
};

export default Modal;