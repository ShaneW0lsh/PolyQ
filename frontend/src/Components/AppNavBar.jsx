import React from "react";
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";

const AppNavBar = () => {
  return (
    <Navbar className="bg-body-tertiary" data-bs-theme="dark">
      <Container>
        <Navbar.Brand>
          {/*<img*/}
          {/*  alt=""*/}
          {/*  src="/logo192.png"*/}
          {/*  width="30"*/}
          {/*  height="30"*/}
          {/*  className="d-inline-block align-top"*/}
          {/*/>{' '}*/}
          PolyQueue
        </Navbar.Brand>
      </Container>
    </Navbar>
  )
}

export default AppNavBar;