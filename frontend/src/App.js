import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import AppNavBar from "./Components/AppNavBar";
import HomePage from "./Components/HomePage";
import SubmissionSessionPage from "./Components/SubmissionSessionPage";

function App() {
  return (
    <Router>
      <div className='App'>
        <AppNavBar />

        <div className='content'>
          <Routes>
            <Route exact path='/' element={<HomePage />} />
            <Route path='/session/:id' element={<SubmissionSessionPage />} />
          </Routes>
        </div>

      </div>
    </Router>
  );
}

export default App;
