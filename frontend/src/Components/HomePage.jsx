import React from "react";
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import {Link} from "react-router-dom";

const HomePage = () => {
  const submissionSessionsList = [
    {
      id: 1,
      disciplineName: "Mathematics",
      startDate: "2023-10-10",
      startTime: "10:30",
      professors: ["Dr. John Doe", "Dr. Jane Smith"]
    },
    {
      id: 2,
      disciplineName: "Physics",
      startDate: "2023-10-12",
      startTime: "14:00",
      professors: ["Dr. Albert Einstein", "Dr. Marie Curie"]
    },
    {
      id: 3,
      disciplineName: "Chemistry",
      startDate: "2023-10-15",
      startTime: "09:45",
      professors: ["Dr. Robert Boyle", "Dr. Antoine Lavoisier"]
    },
    {
      id: 4,
      disciplineName: "Biology",
      startDate: "2023-10-18",
      startTime: "11:15",
      professors: ["Dr. Charles Darwin", "Dr. Gregor Mendel"]
    }
  ];

  return (
    <div>
      {submissionSessionsList.map((session, index) => (
        <Card key={index} className='ms-5 me-5 mt-3'>
          {/*<Card.Header>{session.disciplineName}</Card.Header>*/}
          <Card.Body>
            <Card.Title>{session.disciplineName}</Card.Title>
            <Card.Text>
              <span>
                {session.startTime} {' '}
                {session.startDate} <br/>
              </span>
              {session.professors.join(", ")}
            </Card.Text>
          </Card.Body>
          <Card.Footer>
            <Link to={`/session/${session.id}`} className={'btn btn-primary'}> Зарегистрироваться </Link>
            {/*<Button variant="primary">Зарегистрироваться</Button>*/}
          </Card.Footer>
        </Card>
      ))}
    </div>
  );
}

export default HomePage;