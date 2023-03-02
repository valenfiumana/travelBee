import Swal from 'sweetalert2';

function successMessage(message, timer, position, backdrop) {
  Swal.fire({
    icon: 'success',
    title: message,
    showConfirmButton: false,
    timer: timer,
    position: !position ? 'center' : position,
    backdrop: backdrop === undefined ? true : backdrop
  });
}

export default successMessage
