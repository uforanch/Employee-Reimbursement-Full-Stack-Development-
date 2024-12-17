
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import About from './Components/About'
import Register from './Components/Register'
import Login from './Components/Login'

function App() {

  return (<>
    <div>
    <h1 className='Title'>Rimbsr</h1>
    </div>
    <BrowserRouter>
    <Routes>
      <Route path="/" element={<About/>}/>
      <Route path="/register" element={<Register/>}/>
      <Route path="/login" element={<Login/>}/>

    </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
