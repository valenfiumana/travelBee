import Swal from 'sweetalert2';

function alertMessage(message) {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: `${message}`,
        confirmButtonColor: 'var(--yellow)'
    });
}

export default alertMessage
