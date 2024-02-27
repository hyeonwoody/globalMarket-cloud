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

    const handleMouseOver = () => {

    }

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <div id={"Auth-login"} className={"mt-20"}>
                    <input
                        type="text"
                        placeholder="Username"
                        value={formData.username}
                        className={"w-full"}
                        onChange={handleUsernameChange}
                        style={{
                            padding: '10px',
                            border: '1px solid #ddd',
                            borderRadius: '3px',
                            fontSize: '16px',
                        }}
                    />
                    <br/>
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handlePasswordChange}
                        style={{
                            padding: '10px',
                            border: '1px solid #ddd',
                            borderRadius: '3px',
                            fontSize: '16px',
                        }}
                    />
                    <br/>
                    <button onClick={handleLogin} style={{
                        color: 'white',
                        borderRadius: '7px',
                        cursor: 'pointer',
                        transition: '0.3s ease all',

                    }} className={"mt-2 py-2 px-2 bg-green-500 hover:bg-amber-300 w-full rounded-full"}>Login</button>
                </div>
        </div>
    );
};

export default Auth;