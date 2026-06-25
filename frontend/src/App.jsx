import { useState, useEffect } from 'react'
import axios from 'axios'

const BFF_URL = 'http://localhost:8083/api/v1'

function App() {
  const [reportes, setReportes] = useState([])
  const [alertas, setAlertas] = useState([])
  const [loading, setLoading] = useState(true)
  const [nuevoReporte, setNuevoReporte] = useState({ tipoIncendio: '', coordenadas: '', descripcion: '' })
  const [nuevaAlerta, setNuevaAlerta] = useState({ mensaje: '', nivel: 'CRÍTICO', comuna: '' })

  const fetchData = async () => {
    try {
      const res = await axios.get(`${BFF_URL}/dashboard`)
      setReportes(res.data.reportes || [])
      setAlertas(res.data.alertas || [])
    } catch (e) {
      console.error('Error al conectar con el BFF', e)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchData()
    const interval = setInterval(fetchData, 30000)
    return () => clearInterval(interval)
  }, [])

  const crearReporte = async () => {
    await axios.post('http://localhost:8081/api/v1/reportes', nuevoReporte)
    setNuevoReporte({ tipoIncendio: '', coordenadas: '', descripcion: '' })
    fetchData()
  }

  const crearAlerta = async () => {
    await axios.post('http://localhost:8082/api/v1/alertas', nuevaAlerta)
    setNuevaAlerta({ mensaje: '', nivel: 'CRÍTICO', comuna: '' })
    fetchData()
  }

  if (loading) return <p>Cargando...</p>

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h1>🚨 Sistema Valle del Sol</h1>

      <div style={{ display: 'flex', gap: '40px' }}>

        {/* REPORTES */}
        <div style={{ flex: 1 }}>
          <h2>📋 Reportes ({reportes.length})</h2>
          <div style={{ marginBottom: '10px' }}>
            <input placeholder="Tipo incendio" value={nuevoReporte.tipoIncendio}
              onChange={e => setNuevoReporte({...nuevoReporte, tipoIncendio: e.target.value})} />
            <input placeholder="Coordenadas" value={nuevoReporte.coordenadas}
              onChange={e => setNuevoReporte({...nuevoReporte, coordenadas: e.target.value})} />
            <input placeholder="Descripción" value={nuevoReporte.descripcion}
              onChange={e => setNuevoReporte({...nuevoReporte, descripcion: e.target.value})} />
            <button onClick={crearReporte}>Crear Reporte</button>
          </div>
          {reportes.map(r => (
            <div key={r.id} style={{ border: '1px solid #ccc', padding: '10px', marginBottom: '5px' }}>
              <strong>{r.tipoIncendio}</strong> — {r.estado}<br/>
              📍 {r.coordenadas}<br/>
              📝 {r.descripcion}
            </div>
          ))}
        </div>

        {/* ALERTAS */}
        <div style={{ flex: 1 }}>
          <h2>🔔 Alertas ({alertas.length})</h2>
          <div style={{ marginBottom: '10px' }}>
            <input placeholder="Mensaje" value={nuevaAlerta.mensaje}
              onChange={e => setNuevaAlerta({...nuevaAlerta, mensaje: e.target.value})} />
            <input placeholder="Comuna" value={nuevaAlerta.comuna}
              onChange={e => setNuevaAlerta({...nuevaAlerta, comuna: e.target.value})} />
            <select value={nuevaAlerta.nivel}
              onChange={e => setNuevaAlerta({...nuevaAlerta, nivel: e.target.value})}>
              <option>CRÍTICO</option>
              <option>ALTO</option>
              <option>MEDIO</option>
              <option>BAJO</option>
            </select>
            <button onClick={crearAlerta}>Crear Alerta</button>
          </div>
          {alertas.map(a => (
            <div key={a.id} style={{ border: '1px solid #ccc', padding: '10px', marginBottom: '5px', background: a.nivel === 'CRÍTICO' ? '#ffe0e0' : 'white' }}>
              <strong>{a.nivel}</strong> — {a.comuna}<br/>
              📢 {a.mensaje}
            </div>
          ))}
        </div>

      </div>
    </div>
  )
}

export default App