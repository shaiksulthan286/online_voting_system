import React, { useEffect, useState } from 'react';
import axios from 'axios';
import config from '../config'; // Ensure config.url = 'http://localhost:8080'
// import './Voter.css'; // Uncomment if you have styles

export default function ViewElections() {
  const [elections, setElections] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    axios
      .get(`${config.url}/voter/viewallelections`) // ✅ Changed from /admin/... to /voter/...
      .then(response => {
        setElections(response.data);
      })
      .catch(err => {
        console.error(err);
        setError('Failed to fetch elections.');
      });
  }, []);

  const getStatus = (start, end) => {
    const today = new Date();
    const startDate = new Date(start);
    const endDate = new Date(end);

    if (today < startDate) return 'Upcoming';
    if (today >= startDate && today <= endDate) return 'Ongoing';
    return 'Completed';
  };

  const formatDate = dateStr => {
    const date = new Date(dateStr);
    return date.toLocaleDateString('en-IN', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  return (
    <div className="voter-home-container" style={{ padding: '20px', textAlign: 'center' }}>
      <div className="elections-card" style={{ maxWidth: '900px', margin: '0 auto' }}>
        <h2 style={{ marginBottom: '20px', color: '#333' }}>🗳️ Available Elections</h2>

        {error && <p style={{ color: 'red' }}>{error}</p>}

        {elections.length === 0 ? (
          <p style={{ color: '#666' }}>No elections found.</p>
        ) : (
          <div
            className="election-list"
            style={{
              display: 'grid',
              gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
              gap: '20px',
            }}
          >
            {elections.map(election => (
              <div
                className="election-item"
                key={election.id}
                style={{
                  border: '1px solid #ccc',
                  borderRadius: '10px',
                  padding: '15px',
                  boxShadow: '0 2px 6px rgba(0,0,0,0.1)',
                  backgroundColor: '#fff',
                  textAlign: 'left',
                }}
              >
                <h3 style={{ color: '#007bff', marginBottom: '10px' }}>{election.name}</h3>
                <p>
                  <strong>Type:</strong> {election.electionType}
                </p>
                <p>
                  <strong>City:</strong> {election.city}
                </p>
                <p>
                  <strong>Station:</strong> {election.station}
                </p>
                <p>
                  <strong>Start Date:</strong> {formatDate(election.startDate)}
                </p>
                <p>
                  <strong>End Date:</strong> {formatDate(election.endDate)}
                </p>
                <p>
                  <strong>Status:</strong>{' '}
                  <span
                    style={{
                      color:
                        getStatus(election.startDate, election.endDate) === 'Ongoing'
                          ? 'green'
                          : getStatus(election.startDate, election.endDate) === 'Upcoming'
                          ? 'orange'
                          : 'red',
                      fontWeight: 'bold',
                    }}
                  >
                    {getStatus(election.startDate, election.endDate)}
                  </span>
                </p>
                <p>
                  <strong>Description:</strong> {election.description || 'No description available'}
                </p>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
