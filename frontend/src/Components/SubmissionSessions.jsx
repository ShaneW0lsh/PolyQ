import React from "react";
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

const SubmissionSessions = () => {
  const submissionSessionsList = [
    {
      disciplineName: "Mathematics",
      startDate: "2023-10-10",
      startTime: "10:30",
      professors: ["Dr. John Doe", "Dr. Jane Smith"]
    },
    {
      disciplineName: "Physics",
      startDate: "2023-10-12",
      startTime: "14:00",
      professors: ["Dr. Albert Einstein", "Dr. Marie Curie"]
    },
    {
      disciplineName: "Chemistry",
      startDate: "2023-10-15",
      startTime: "09:45",
      professors: ["Dr. Robert Boyle", "Dr. Antoine Lavoisier"]
    },
    {
      disciplineName: "Biology",
      startDate: "2023-10-18",
      startTime: "11:15",
      professors: ["Dr. Charles Darwin", "Dr. Gregor Mendel"]
    }
  ];

  return (
    <div>
      {submissionSessionsList.map((session, index) => (
        <Card key={index} className='mb-3'>
          <Card.Header>{session.disciplineName}</Card.Header>
          <Card.Body>
            <Card.Title>Session Details</Card.Title>
            <Card.Text>
              <strong>Start Date:</strong> {session.startDate}<br />
              <strong>Start Time:</strong> {session.startTime}<br />
              <strong>Professors:</strong> {session.professors.join(", ")}
            </Card.Text>
            <Button variant="primary">Go somewhere</Button>
          </Card.Body>
        </Card>
      ))}
    </div>
  );
}

export default SubmissionSessions;