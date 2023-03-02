import { useContext, useEffect } from "react";
import { UserContext } from "../../../context/UserContext";
import HeaderBtn from "./HeaderBtn";

function DefaultHeader() {
    const currentPath = window.location.pathname;
    const { setIsLogged } = useContext(UserContext);

    useEffect(() => {
        localStorage.getItem('jwt') && setIsLogged(true);
    },[]);
    
    return (
        <ul className="nav-tabs">
            {currentPath !== '/register' && <HeaderBtn type={'Register'} name='Registrarme' />}
            {currentPath !== '/login' && <HeaderBtn type={'Login'} name='Iniciar sesión' />}
        </ul>
    )
}

export default DefaultHeader
