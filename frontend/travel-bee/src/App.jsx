import { BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import './App.css'
import Header from './components/Header/Header'
import Footer from './components/Footer/Footer'
import Register from './components/Register-Login/Register'
import Login from './components/Register-Login/Login'
import Main from './components/Main/Main'
import { UserContextProvider } from './context/UserContext'
import Error404 from './assets/404'
import { ProductContextProvider } from './context/ProductContext'
import ProductRouting from './components/ProdutcDetails/ProductRouting'
import ScrollToTop from './utils/ScrollToTop'
import MyBookings from './components/MyBookings/MyBookings'
import MyPublications from './components/MyPublications/MyPublications'
import { PublicationContextProvider } from './context/PublicationContext'

function App() {
  return (
    <div className="App">
      <UserContextProvider>
        <ProductContextProvider>
          <PublicationContextProvider>        
            <Router>
              <ScrollToTop />
              <Header />
              <Routes>
                <Route exact path="/" element={<Main />} />
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
                <Route path="/residencia/:product_id/*" element={<ProductRouting />} />
                <Route path="/editar/:product_id/*" element={<MyPublications />} />
                <Route path="/:user_id/misReservas/*" element={<MyBookings />} />
                <Route path="/:user_id/misPublicaciones/*" element={<MyPublications />} />
                <Route path="*" element={<Error404 />} />
              </Routes>
              <Footer />
            </Router>
          </PublicationContextProvider>
        </ProductContextProvider>  
      </UserContextProvider>
    </div>
  );
}

export default App
