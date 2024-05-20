import './App.css';
import logoDataParser from './imagenes/logodataparser.png';
import BarraNav from './componentes/BarraNav';

function App() {
  return (
    <div className='contenedor-principal'>
      <div className='freecodecamp-logo-contenedor'>
        <img 
          src={logoDataParser} 
          className='freecodecamp-logo' />
      </div>
      <div>
        <BarraNav />
      </div>
      <div className='pie-de-pagina'>
          Hola mundo
      </div>
    </div>
  );
}

export default App;
