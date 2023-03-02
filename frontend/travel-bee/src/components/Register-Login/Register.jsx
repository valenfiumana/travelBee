import React, { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { UserContext } from "../../context/UserContext";
import { CloseOutlined } from '@ant-design/icons';
import './Register.css'
import postData from "../../utils/postData";
import alertMessage from "../../utils/alertMessage";
import successMessage from "../../utils/successMessage";

function Register(props) {
  const navigateTo = useNavigate();
  const [providedData, setProvidedData] = useState({firstName: '', lastName: '', email: '', password: '', passwordCheck: ''})
  const { setIsLogged } = useContext(UserContext);
  const { setUser } = useContext(UserContext);
  const baseURL = import.meta.env.VITE_BASE_URL;
  const registerEndPoint = `${baseURL}/signup`

  function handleSubmit (e) {
    e.preventDefault();
    if (providedData.password !== providedData.passwordCheck) {
      alertMessage('Las contraseñas no coinciden');             
    } else {

      const registerData = {
        firstName: providedData.firstName,
        lastName: providedData.lastName,
        email: providedData.email,
        password: providedData.password,
      }

      postData(registerEndPoint, registerData)
      .then(res => {
        if (res) {
          if (res.status === 201) {
            successMessage('Registro exitoso!', 1500);
            setTimeout(() => {
              navigateTo('/login');
            }, 2000);
          }
        }
      });
    }
  }
 
  return (
    <div className="registro-container">
      <form onSubmit={handleSubmit}>
        <div className="titulo-registro">
          <Link className='close-icon' to="/"><CloseOutlined style={{ fontSize: '24px', color: 'black' }} /></Link>
          <h1>Nos encanta que estés acá</h1>
          <label>Nombre</label>
          <input autoFocus onChange={(e) => setProvidedData({
            ...providedData, firstName: e.target.value
          })}   required autoComplete="off" placeholder="Nombre" />
          <label>Apellido</label>
          <input onChange={(e) => setProvidedData({
            ...providedData, lastName: e.target.value
          })} required autoComplete="off" placeholder="Apellido" />
          <label>Correo electrónico</label>
          <input type='email' onChange={(e) => setProvidedData({
            ...providedData, email: e.target.value
          })} required autoComplete="off" placeholder="ejemplo@ejemplo.com"/>
          <label>Contraseña</label>
          <input type='password' minLength='6' id="password" onChange={(e) => setProvidedData({
            ...providedData, password: e.target.value
          })} required autoComplete="off" placeholder="*********" />
          <label>Confirmar contraseña</label>
          <input type='password' minLength='6' id="password_check" onChange={(e) => setProvidedData({
            ...providedData, passwordCheck: e.target.value
          })} required autoComplete="off" placeholder="*********" />
          <div className="registro-partes button">
            <button type="submit">Registrarme</button>
          </div>
          <div className="ingresar">
            Ya tienes cuenta?
            <span className="button:hover">
              <Link to="/login"> Iniciar sesion</Link>
            </span>
          </div>
        </div>
      </form>
    </div>
  );
}

export default Register;
