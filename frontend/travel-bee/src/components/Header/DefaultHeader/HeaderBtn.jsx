import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../../context/UserContext';

function HeaderBtn({type, name}) {
    const { setIsLogged } = useContext(UserContext);

    const navigateTo = useNavigate();
    function handleClick() {
        navigateTo(`/${type.toLowerCase()}`)
        document.getElementById("menuToggle").checked = false;
    }
    return (
        <li className="nav-tab">
            <button id={type.toLowerCase()} type='button' onClick={handleClick}>{name}</button>
        </li>
    );
}
    
export default HeaderBtn;
