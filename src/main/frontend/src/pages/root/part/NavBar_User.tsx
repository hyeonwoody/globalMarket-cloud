import React from 'react';
import {useAuth} from "../../../store/AuthContext";
function NavBar_User() {
    const { authState, login, logout } = useAuth();
    return (
        <div className="Container">
            <div className="md:flex md:w-auto" id="navbar_user">
                <ul className="flex flex-col font-medium p-4 md:p-0 mt-4 mr-4 border border-gray-100 rounded-lg md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700 ml-auto">
                    {authState.isLoggedIn ? <p className="text-white">{authState.username}님, 안녕하세요</p> : null}
                </ul>
                <svg className="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true"
                     xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
                    <path
                        d="M17.1 12.6v-1.8A5.4 5.4 0 0 0 13 5.6V3a1 1 0 0 0-2 0v2.4a5.4 5.4 0 0 0-4 5.5v1.8c0 2.4-1.9 3-1.9 4.2 0 .6 0 1.2.5 1.2h13c.5 0 .5-.6.5-1.2 0-1.2-1.9-1.8-1.9-4.2ZM8.8 19a3.5 3.5 0 0 0 6.4 0H8.8Z"/>
                </svg>

                <ul className="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
                    {authState.isLoggedIn ? <li>
                        <a href="/"
                           className="block py-2 px-3 text-white rounded md:bg-transparent md:text-blue-700 md:p-0 md:dark:text-blue-500 dark:bg-blue-600 md:dark:bg-transparent"
                           aria-current="page">로그아웃</a>
                    </li> :null
                    }
                </ul>
            </div>
        </div>

    );
}

export default NavBar_User;