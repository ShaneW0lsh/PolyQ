import React from "react";
import {useParams} from "react-router-dom";

const SubmissionSessionPage = () => {
  const { id } = useParams();
  return (<h1>submission session page {id}</h1>);
}

export default SubmissionSessionPage;