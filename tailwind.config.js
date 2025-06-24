/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx}',  // Scan all files in the pages folder
    './components/**/*.{js,ts,jsx,tsx}',  // Scan all files in the components folder
    './app/**/*.{js,ts,jsx,tsx}',  // If using the new app directory in Next.js
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
