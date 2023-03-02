import { useContext, useEffect } from 'react'
import { UserContext } from '../../../context/UserContext'
import { Avatar } from 'antd'
import Swal from 'sweetalert2'
import successMessage from '../../../utils/successMessage';
import { useNavigate } from 'react-router-dom';
import jwt_decode from "jwt-decode";

function LoggedHeader () {
  const {user, setIsLogged} = useContext(UserContext);
  const [userValue, setUserValue] = user;
  const userInitials = userValue?.firstName.charAt(0).toUpperCase() + userValue?.lastName.charAt(0).toUpperCase();
  const navigateTo = useNavigate();
  const avatarColor = userValue?.role === 1 ? 'var(--yellow)' : '#ff3d3d'

  useEffect(() => {
    if (localStorage.getItem('jwt')) {
      const jwt = localStorage.getItem('jwt');
      const decodedJwt = jwt_decode(jwt);
      setUserValue({
        firstName: decodedJwt.name,
        lastName: decodedJwt.lastname,
        email: decodedJwt.sub,
        userId: decodedJwt.id,
        role: decodedJwt.role,
      });
    }
  },[]);

  function logOut() {

    Swal.fire({
      title: '¿Seguro desea cerrar sesión?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: 'var(--yellow)',
      confirmButtonText: 'Sí',
      cancelButtonColor: 'black',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        localStorage.clear();
        setUserValue({
          firstName: '',
          lastName: '',
          email: '',
          userId: '',
          role: '',
        });
        setIsLogged(false);
        successMessage('Hasta pronto 👋', 2000);
      }
    })
  }

  function handleClick(e) {
    e.target.className === 'myBookings' && navigateTo(`/${userValue?.userId}/misReservas`);
    e.target.className === 'myPublications' && navigateTo(`/${userValue?.userId}/misPublicaciones`);
  }

  return (
    <div className='loggedDesktop'>
      <div className='logged-header'>
        <div className='my-bookings'>
          {userValue?.role === 1 && <h3 onClick={handleClick} className='myBookings'>Mis reservas</h3>}
          {userValue?.role === 2 && <h3 onClick={handleClick} className='myPublications'>Crear publicación</h3>}
        </div>  
        <Avatar style={{ color:'#464242', backgroundColor: `${avatarColor}`, fontWeight: '600' }}>{userInitials}</Avatar>
        <h3>Hola {userValue.firstName}</h3>
      </div>
      <hr className='hr'/>
      <div className='botonCerrarSesion'>
        <button onClick={logOut}>Cerrar sesión</button>
      </div>
    </div>
  )
}

export default LoggedHeader
