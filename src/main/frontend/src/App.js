import logo from './logo.svg';
import './App.css';

import Root from './pages/root/Root.tsx'
import {Routes, Route, BrowserRouter} from 'react-router-dom';
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Root />}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
