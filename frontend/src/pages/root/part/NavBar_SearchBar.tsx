import React from 'react';

function NavBar_SearchBar() {
    return (
        <div className="Container" style={{display: 'flex',position: 'relative'}}>
            <input type="text" placeholder="검색하기" autoComplete="off" inputMode="none"
                   style={{flex: 1,
                       paddingLeft: '30px',
                            width: '100%',
                            fontSize: '16px',
                            borderRadius: '8px',
                            }}/>
            <svg
                className="w-6 h-6 text-gray-800 dark:text-white"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                style={{position: 'absolute', top: '50%', left: '8px', transform: 'translateY(-50%)'}}
            >
                <path stroke="black" d="m21 21-3.5-3.5M17 10a7 7 0 1 1-14 0 7 7 0 0 1 14 0Z"/>

            </svg>

        </div>
    );
}

export default NavBar_SearchBar;