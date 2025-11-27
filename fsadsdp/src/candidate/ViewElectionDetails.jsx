import React, { useCallback, useEffect, useMemo, useState } from 'react';
import axios from 'axios';
import config from '../config';
import './../voter/Voter.css';

export default function ViewElectionDetails() {
  const [elections, setElections] = useState([]);
  const [registrations, setRegistrations] = useState([]);
  const [error, setError] = useState('');
  const [info, setInfo] = useState('');
  const [loading, setLoading] = useState(true);
  const [submittingFor, setSubmittingFor] = useState(null);

  const candidate = useMemo(() => {
    const stored = sessionStorage.getItem('candidate');
    return stored ? JSON.parse(stored) : null;
  }, []);

  const candidateId = candidate?.id;

  const fetchRegistrations = useCallback(async () => {
    if (!candidateId) return;
    const registrationResponse = await axios.get(
      `${config.url}/candidate/${candidateId}/election-registrations`
    );
    setRegistrations(registrationResponse.data || []);
  }, [candidateId]);

  useEffect(() => {
    if (!candidateId) {
      setError('Candidate details not found. Please log in again.');
      setLoading(false);
      return;
    }

    const loadData = async () => {
      try {
        setLoading(true);
        const electionResponse = await axios.get(`${config.url}/voter/viewallelections`);
        setElections(electionResponse.data || []);
        await fetchRegistrations();
        setError('');
      } catch (err) {
        console.error(err);
        setError('Failed to load elections. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [candidateId, fetchRegistrations]);

  const registrationByElection = useMemo(() => {
    return registrations.reduce((acc, registration) => {
      acc[registration.electionId] = registration;
      return acc;
    }, {});
  }, [registrations]);

  const getStatusLabel = (start, end) => {
    const today = new Date();
    const startDate = new Date(start);
    const endDate = new Date(end);

    if (today < startDate) return 'Upcoming';
    if (today >= startDate && today <= endDate) return 'Ongoing';
    return 'Completed';
  };

  const formatDate = dateStr => {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    return date.toLocaleDateString('en-IN', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  const handleRegister = async electionId => {
    if (!candidateId) return;
    setSubmittingFor(electionId);
    setInfo('');
    setError('');

    try {
      await axios.post(`${config.url}/candidate/election/register`, {
        candidateId,
        electionId,
      });

      await fetchRegistrations();
      setInfo('Registration submitted successfully. Awaiting admin approval.');
    } catch (err) {
      const message =
        err.response?.data ||
        err.message ||
        'Unable to submit registration right now.';
      setError(typeof message === 'string' ? message : 'Registration failed.');
    } finally {
      setSubmittingFor(null);
    }
  };

  const renderAction = election => {
    const registration = registrationByElection[election.id];
    const status = registration?.status;

    if (status === 'APPROVED') {
      return <span style={{ color: 'green', fontWeight: 'bold' }}>Approved</span>;
    }

    if (status === 'PENDING') {
      return <span style={{ color: 'orange', fontWeight: 'bold' }}>Pending Approval</span>;
    }

    const electionStatus = getStatusLabel(election.startDate, election.endDate);
    const disabled = electionStatus === 'Completed';
    const label = status === 'REJECTED' ? 'Reapply' : 'Register';

    return (
      <button
        onClick={() => handleRegister(election.id)}
        disabled={disabled || submittingFor === election.id}
        style={{ marginTop: '10px' }}
      >
        {submittingFor === election.id ? 'Submitting...' : label}
      </button>
    );
  };

  return (
    <div className="voter-home-container" style={{ padding: '20px', textAlign: 'center' }}>
      <div className="elections-card" style={{ maxWidth: '960px', margin: '0 auto' }}>
        <h2 style={{ marginBottom: '20px', color: '#333' }}>Register for Elections</h2>

        {loading && <p>Loading elections...</p>}
        {info && <p style={{ color: 'green' }}>{info}</p>}
        {error && <p style={{ color: 'red' }}>{error}</p>}

        {!loading && elections.length === 0 && (
          <p style={{ color: '#666' }}>No elections available right now.</p>
        )}

        {!loading && elections.length > 0 && (
          <div
            className="election-list"
            style={{
              display: 'grid',
              gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))',
              gap: '20px',
            }}
          >
            {elections.map(election => {
              const electionStatus = getStatusLabel(election.startDate, election.endDate);
              const registration = registrationByElection[election.id];

              return (
                <div
                  className="election-item"
                  key={election.id}
                  style={{
                    border: '1px solid #ccc',
                    borderRadius: '10px',
                    padding: '18px',
                    boxShadow: '0 2px 6px rgba(0,0,0,0.1)',
                    backgroundColor: '#fff',
                    textAlign: 'left',
                  }}
                >
                  <h3 style={{ color: '#007bff', marginBottom: '10px' }}>{election.name}</h3>
                  <p><strong>Type:</strong> {election.electionType}</p>
                  <p><strong>City:</strong> {election.city}</p>
                  <p><strong>Station:</strong> {election.station}</p>
                  <p><strong>Start Date:</strong> {formatDate(election.startDate)}</p>
                  <p><strong>End Date:</strong> {formatDate(election.endDate)}</p>
                  <p>
                    <strong>Status:</strong>{' '}
                    <span
                      style={{
                        color:
                          electionStatus === 'Ongoing'
                            ? 'green'
                            : electionStatus === 'Upcoming'
                            ? 'orange'
                            : 'red',
                        fontWeight: 'bold',
                      }}
                    >
                      {electionStatus}
                    </span>
                  </p>
                  {registration && (
                    <p>
                      <strong>Registration Status:</strong> {registration.status}
                    </p>
                  )}
                  {renderAction(election)}
                </div>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
}
