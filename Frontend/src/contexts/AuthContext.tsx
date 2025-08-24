import React, { createContext, useState, type ReactNode } from 'react';

// Define the shape of the authentication state
interface AuthState {
  token: string | null;
  role: 'ADMIN' | 'PATIENT' | 'DOCTOR' | null;
  isAuthenticated: boolean;
}

// Define the shape of the context value
interface AuthContextType extends AuthState {
  login: (token: string, role: 'ADMIN' | 'PATIENT' | 'DOCTOR') => void;
  logout: () => void;
}

// Create the context with a default value
export const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Define props for the provider component
interface AuthProviderProps {
  children: ReactNode;
}

// Create the provider component
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [auth, setAuth] = useState<AuthState>({
    token: localStorage.getItem('token'),
    role: localStorage.getItem('role') as 'ADMIN' | 'PATIENT' | 'DOCTOR' | null,
    isAuthenticated: !!localStorage.getItem('token'),
  });

  // Function to handle user login
  const login = (token: string, role: 'ADMIN' | 'PATIENT' | 'DOCTOR') => {
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
    setAuth({ token, role, isAuthenticated: true });
  };

  // Function to handle user logout
  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    setAuth({ token: null, role: null, isAuthenticated: false });
  };

  // The value provided to consuming components
  const contextValue: AuthContextType = {
    ...auth,
    login,
    logout,
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {children}
    </AuthContext.Provider>
  );
};
