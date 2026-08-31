const KEY='studentApplicationSystem_v1';
const $=s=>document.querySelector(s);
let applications=JSON.parse(localStorage.getItem(KEY)||'[]');

function save(){localStorage.setItem(KEY,JSON.stringify(applications));render();}
function id(){return `APP-${new Date().getFullYear()}-${String(applications.length+1).padStart(4,'0')}`;}
function esc(v=''){return String(v).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#039;'}[c]));}
function statusClass(s){return {'Pending':'pending','Under Review':'review','Accepted':'accepted','Rejected':'rejected'}[s]||'pending';}
function render(){
  $('#totalApplications').textContent=applications.length;
  $('#pendingApplications').textContent=applications.filter(a=>a.status==='Pending').length;
  $('#acceptedApplications').textContent=applications.filter(a=>a.status==='Accepted').length;
  const q=($('#searchInput')?.value||'').toLowerCase();
  const f=$('#filterStatus')?.value||'All';
  const rows=applications.filter(a=>(f==='All'||a.status===f)&&JSON.stringify(a).toLowerCase().includes(q));
  $('#applicationTable').innerHTML=rows.map(a=>`<tr>
    <td>${esc(a.id)}</td><td><strong>${esc(a.firstName)} ${esc(a.lastName)}</strong></td>
    <td>${esc(a.program)}</td><td>${esc(a.email)}</td><td>${esc(a.date)}</td>
    <td><span class="pill ${statusClass(a.status)}">${esc(a.status)}</span></td>
    <td><button class="action" onclick="changeStatus('${a.id}')">Change</button></td>
  </tr>`).join('');
  $('#emptyTable').style.display=rows.length?'none':'block';
}
function toast(msg){const t=$('#toast');t.textContent=msg;t.classList.add('show');setTimeout(()=>t.classList.remove('show'),3500)}

$('#applicationForm').addEventListener('submit',e=>{
  e.preventDefault();
  const data=Object.fromEntries(new FormData(e.target).entries());
  const application={...data,id:id(),date:new Date().toISOString().slice(0,10),status:'Pending'};
  applications.push(application);save();e.target.reset();
  location.hash='status';$('#statusQuery').value=application.id;
  checkStatus();toast(`Application submitted successfully. ID: ${application.id}`);
});
function checkStatus(){
  const q=$('#statusQuery').value.trim().toLowerCase();
  const a=applications.find(x=>x.id.toLowerCase()===q||x.email.toLowerCase()===q);
  $('#statusResult').innerHTML=a?`<div class="result"><strong>${esc(a.firstName)} ${esc(a.lastName)}</strong><br>Application: ${esc(a.id)}<br>Program: ${esc(a.program)}<br>Status: <span class="pill ${statusClass(a.status)}">${esc(a.status)}</span></div>`:'<div class="result">No application was found. Check the ID or email and try again.</div>';
}
$('#statusBtn').addEventListener('click',checkStatus);
$('#searchInput').addEventListener('input',render);$('#filterStatus').addEventListener('change',render);
function changeStatus(applicationId){
  const a=applications.find(x=>x.id===applicationId); if(!a)return;
  const next=prompt('Enter status: Pending, Under Review, Accepted, or Rejected',a.status);
  if(['Pending','Under Review','Accepted','Rejected'].includes(next)){a.status=next;save();toast('Application status updated.')}
}
$('#clearAll').addEventListener('click',()=>{if(confirm('Delete all demo applications from this browser?')){applications=[];save();toast('Demo data cleared.')}});

render();
