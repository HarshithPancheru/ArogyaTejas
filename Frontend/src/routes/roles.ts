export const ROLES = {
  ADMIN: 'ADMIN',
  PATIENT: 'PATIENT',
  DOCTOR: 'DOCTOR',
} as const; // 'as const' makes the values readonly and specific strings

export type Role = typeof ROLES[keyof typeof ROLES];
