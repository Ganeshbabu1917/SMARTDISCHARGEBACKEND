-- This will automatically insert data when app starts
INSERT IGNORE INTO patients (patient_id, name, age, gender, diagnosis, admission_date, 
                           discharge_date, medications, vitals, lab_results, doctor_notes) 
VALUES 
('P001', 'John Doe', 65, 'Male', 'Hypertension, Type 2 Diabetes', '2024-01-15', 
 '2024-01-20', 'Metformin 500mg twice daily, Lisinopril 10mg once daily', 
 'BP: 135/85, HR: 72, Temp: 98.6°F, O2: 98%', 
 'Blood Sugar: 140 mg/dL, HbA1c: 7.2%, Creatinine: 0.9', 
 'Patient stable, blood pressure controlled. Continue current medications. Follow up in 2 weeks.'),

('P002', 'Jane Smith', 45, 'Female', 'Community Acquired Pneumonia', '2024-01-16', 
 '2024-01-19', 'Amoxicillin 500mg three times daily, Azithromycin 250mg once daily', 
 'Temp: 98.6°F, HR: 80, BP: 120/80, O2: 98%', 
 'Chest X-ray: Improving, WBC: 8.5, CRP: 10', 
 'Good response to antibiotics. Complete 7-day course. Follow up in 1 week.'),

('P003', 'Robert Johnson', 72, 'Male', 'Congestive Heart Failure', '2024-01-17', 
 '2024-01-21', 'Furosemide 40mg daily, Lisinopril 5mg daily, Metoprolol 25mg daily', 
 'BP: 128/75, HR: 68, Weight: 75kg, O2: 96%', 
 'BNP: 450, Echo: EF 45%, Chest X-ray: Mild congestion', 
 'Diuresis achieved. Weight down 2kg. Continue heart failure medications. Low sodium diet.'),
 
('P004', 'Maria Garcia', 35, 'Female', 'Acute Bronchitis', '2024-01-18', 
 '2024-01-20', 'Albuterol inhaler as needed, Dextromethorphan syrup', 
 'Temp: 99.1°F, HR: 82, RR: 18, O2: 99%', 
 'Chest X-ray: Clear, Influenza: Negative', 
 'Viral bronchitis. Symptomatic treatment. Return if fever persists.');