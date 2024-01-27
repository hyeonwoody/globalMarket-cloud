import React, { createContext, useReducer, useContext, ReactNode } from 'react';

// Initial state
type PageState = {
    page: string[],

};

// Action types
type PageAction =
    | {type: 'CLEAR'}
    | { type: 'MOVE'; payload: {target: string}}
    | { type: 'BACK'}
    | { type: 'FORWARD'; payload: {target: string}};

const initialState: PageState = {
    page: ["로그인"],
};

type PageContextType = {
    pageState: PageState;
    clear: () => void;
    move: (page: string) => void;
    back: () => void;
    forward: (page: string) => void;
};

const PageContext = createContext<PageContextType | undefined>(undefined);

// AuthProvider component
type PageProviderProps = {
    children: ReactNode;
}

const pageReducer: React.Reducer<PageState, PageAction> = (state, action) => {
    switch (action.type) {
        case 'CLEAR':
            console.log("CLEAR")
            return {
                ...state,
                page: []
            }
        case 'MOVE':
            console.log("MOVE" + action.payload.target)
            return {
                ...state,
                page: [...state.page, action.payload.target]
            };
        case 'BACK':
            return {
                ...state,
                page: state.page.slice(0, -1) // Create a new array without the last element
            };
        case 'FORWARD':
            return {
                ...state,
                page: [...state.page, action.payload.target]
            };
        default:
            // Ensure that the default case returns the current state
            return state;
    }
};

export const PageProvider: React.FC<PageProviderProps> = ({ children }) => {

    const [pageState, dispatch] = useReducer<React.Reducer<PageState, PageAction>>(pageReducer, initialState);
    const clear = (): void =>{
        dispatch({
            type: 'CLEAR',
        })
    }
    const move = (target: string): void => {
        dispatch({
            type: 'FORWARD',
            payload: { target },
        });
    }

    const back = (): void =>{
        dispatch({
           type: 'BACK',
        });
    }
    const forward = (target: string): void => {
        dispatch({
            type: 'FORWARD',
            payload: { target },
        });
    }


    return (

        <PageContext.Provider value={{ pageState, clear, move, back, forward}}>
            {children}
        </PageContext.Provider>
    );
};

export const usePage = () => {
    const context = useContext(PageContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};