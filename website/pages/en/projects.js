const React = require('react');

const CompLibrary = require('../../core/CompLibrary.js');

const Container = CompLibrary.Container;
const GridBlock = CompLibrary.GridBlock;

function Projects(props) {

  const projectLinks = [
    {
      content: 'Scala implementation of URI Template ([RFC 6570](https://tools.ietf.org/html/rfc6570)).',
      title: '[uritemplate4s](https://github.com/Slakah/uritemplate4s)',
    },
  ];

  return (
    <div className="docMainWrapper wrapper">
      <Container className="mainContainer documentContainer postContainer">
        <div className="post">
          <header className="postHeader">
            <h1>Projects</h1>
          </header>
          <p>Some of the projects I'm currently working on</p>
          <GridBlock contents={projectLinks} layout="threeColumn" />
        </div>
      </Container>
    </div>
  );
}

module.exports = Projects;
