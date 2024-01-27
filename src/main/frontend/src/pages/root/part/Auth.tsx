import React, {useState} from 'react';
import { useAuth } from '../../../store/AuthContext';

interface AuthProps{
    authLogin: (username : string) => void,
    authLogout: () => void,
}

const Auth = (props: AuthProps) => {
    const { authState, login, logout } = useAuth();
    const [formData, setFormData] = useState({ username: '', password: '' });
    const handleLogin = () => {
        // Simulate a login request (replace with actual authentication logic)
        // For simplicity, we're just checking if the username is not empty
        login(formData.username)
    };

    const handleLogout = () => {
        logout();
    };

    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { value } = e.target;
        setFormData({ ...formData, username: value });
    };

    const handlePasswordChange = (e : React.ChangeEvent<HTMLInputElement>) =>{
        const { value } = e.target;
        setFormData({ ...formData, password: value });
    }

    return (
        <div>
                <div>
                    <input
                        type="text"
                        placeholder="Username"
                        value={formData.username}
                        onChange={handleUsernameChange}
                    />
                    <br/>
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handlePasswordChange}
                    />
                    <br/>
                    <button onClick={handleLogin}>Login</button>
                </div>
        </div>
    );
};

export default Auth;