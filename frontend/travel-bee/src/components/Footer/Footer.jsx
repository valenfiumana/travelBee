import { TwitterOutlined, FacebookFilled, YoutubeFilled, InstagramFilled } from '@ant-design/icons';
import "./Footer.css"

const Footer = () => {

  return (
    <div className="footer-container">
      <div className="footer-left">
        <p>© 2022 TravelBee. Todos los derechos reservados.</p>
      </div>
      <div className="footer-right">
        <a className='twitter' href="https://www.instagram.com/travelbee.ok/" target="_blank"><TwitterOutlined /></a>
        <a className='facebook' href="https://www.instagram.com/travelbee.ok/" target="_blank"><FacebookFilled /></a>
        <a className='youtube' href="https://www.instagram.com/travelbee.ok/" target="_blank"><YoutubeFilled /></a>
        <a className='instagram' href="https://www.instagram.com/travelbee.ok/" target="_blank"><InstagramFilled /></a>
      </div>
    </div>
  );
};
  
export default Footer
