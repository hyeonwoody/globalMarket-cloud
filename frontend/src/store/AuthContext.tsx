import React, { createContext, useReducer, useContext, ReactNode } from 'react';

// Initial state
type AuthState = {
    isLoggedIn: boolean,
    username: string,
};

// Action types
type AuthAction =
    | { type: 'LOGIN'; payload: { username: string } }
    | { type: 'LOGOUT' };

type AuthContextType = {
    authState: AuthState;
    login: (username: string) => void;
    logout: () => void;
};

const initialState: AuthState = {
    isLoggedIn: false,
    username: '',
};
// Reducer function

const authReducer: React.Reducer<AuthState, AuthAction> = (state, action) => {
    switch (action.type) {
        case 'LOGIN':
            return {
                ...state,
                isLoggedIn: true,
                username: action.payload.username,
            };
        case 'LOGOUT':
            return {
                ...state,
                isLoggedIn: false,
                username: '',
            };
        default:
            // Ensure that the default case returns the current state
            return state;
    }
};
// Create context
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// AuthProvider component
type AuthProviderProps = {
    children: ReactNode;
}
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [authState, dispatch] = useReducer<React.Reducer<AuthState, AuthAction>>(authReducer, initialState);

    const login = (username: string): void => {
        dispatch({
            type: 'LOGIN',
            payload: { username },
        });
    };

    const logout = (): void => {
        dispatch({
            type: 'LOGOUT',
        });
    };


    return (
        <AuthContext.Provider value={{ authState, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

// Custom hook for using the auth context
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};