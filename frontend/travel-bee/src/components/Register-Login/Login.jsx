import React, { useContext } from "react";
import { useState, useEffect  } from "react";
import { Link, useNavigate, useLocation} from "react-router-dom";
import { UserContext } from "../../context/UserContext";
import { CloseOutlined } from '@ant-design/icons';
import './Login.css';
import postData from "../../utils/postData";
import successMessage from "../../utils/successMessage";

function Login() {
  const [ errorExists, setErrorExists] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [providedData, setProvidedData] = useState({email: '', password: ''})
  const { setIsLogged } = useContext(UserContext);
  const [message, setMessage] = useState("");
  const location = useLocation();
  const { bookingMessage, productId } = location.state ? location.state : false;
  const navigateTo = useNavigate();
  const baseURL = import.meta.env.VITE_BASE_URL;
  const loginEndPoint = `${baseURL}/signin`
  const errorMessages = {emailError: '', passwordError: ''}

  function handleSubmit (e) {
    e.preventDefault();
    setIsSubmitted(true);

    const loginData = {
      email: providedData.email,
      password: providedData.password
    }

    postData(loginEndPoint, loginData)
    .then(res => {
      
      if (res) {
        localStorage.setItem('jwt', res.data.jwt);
        setIsLogged(true);
        successMessage('Login exitoso!', 1500);
        setTimeout(() => {
          bookingMessage ? navigateTo(`/residencia/${productId}/reserva`) : navigateTo('/')
        }, 1500);
      }
    });
  }

  useEffect(() => {
    if (bookingMessage) {
      setMessage(<span className='booking-message'>{bookingMessage}</span>);
    }
  }, []);

  return (
    <div className="login-container">
      <form onSubmit={ handleSubmit }>
        <div className="login-form">
          <Link className='close-icon' to="/"><CloseOutlined style={{ fontSize: '24px', color: 'black' }} /></Link>
          <h1>¡Hola de nuevo!</h1>
          <p>{message}</p>
          <div className="input-container">
            <label>Correo electrónico</label>
            <input autoFocus onChange={(e) => setProvidedData({
              ...providedData, email: e.target.value
            })} type="email" name="uname" required autoComplete="off" placeholder="ejemplo@ejemplo.com" />
            { errorExists && <div>{errorMessages.emailError}</div> }
          </div>
          <div className="input-container">
            <label>Contraseña</label>
            <input onChange={(e) => setProvidedData({
              ...providedData, password: e.target.value
            })} type="password" name="pass" required autoComplete="off" placeholder="*********"/>
            { errorExists && <div>{errorMessages.passwordError}</div> }
          </div>
          <div className="login-form button:hover">
          <button type="submit" >Ingresar</button>    
          </div>
          <div className="registrarme">
            ¿Aún no tienes cuenta?
            <span>
              <Link to="/register"> Registrarme</Link>
            </span>
          </div>
        </div>
      </form>
    </div>
  );
}

export default Login;
