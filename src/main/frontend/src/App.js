
import Root from './pages/root/Root.tsx'

import {Routes, Route, BrowserRouter} from 'react-router-dom';
import {AuthProvider} from "./store/AuthContext";
import {PageProvider} from "./store/PageContext";

function App() {
  return (
    <div className="App">
        <PageProvider>


    <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Root />}/>
                </Routes>
            </BrowserRouter>
    </AuthProvider>
        </PageProvider>
    </div>
  );
}

export default App;
