import './Header.css'
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../context/UserContext';
import { useContext } from 'react';
import LoggedHeader from './LoggedHeader/LoggedHeader';
import DefaultHeader from './DefaultHeader/DefaultHeader';
import { MenuOutlined } from '@ant-design/icons';

function Header() {
  const userContext = useContext(UserContext);
  const isLogged = userContext.isLogged
  const navigateTo = useNavigate();
  function handleClick() {
      navigateTo('/')
  }

  return (
    <header style={{right: '0px'}}>
        <div className="nav-wrapper">
            <div onClick={handleClick} className="logo-container">
              <img  src="https://travel-bee-images.s3.us-east-2.amazonaws.com/brand/logoBee.png" alt="" />
              <p><strong>travel</strong>Bee</p>
            </div>
            <nav>
                <input className="hidden" type="checkbox" id="menuToggle"/>
                <label className="menu-btn" htmlFor="menuToggle"><MenuOutlined /></label>
                <div className="nav-container">
                  { isLogged ? <LoggedHeader /> : <DefaultHeader /> }
                </div>
            </nav>
        </div>
    </header>
  );
}
  
export default Header
