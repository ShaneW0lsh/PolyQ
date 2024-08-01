import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import AppNavBar from "./Components/AppNavBar";
import SubmissionSessions from "./Components/SubmissionSessions";

function App() {
  return (
    <Router>
      <div className='App'>
        <AppNavBar />

        <div className='content'>
          <Routes>
            <Route path='/' element={<SubmissionSessions />} />
          </Routes>
        </div>

      </div>
    </Router>
  );
}

export default App;
