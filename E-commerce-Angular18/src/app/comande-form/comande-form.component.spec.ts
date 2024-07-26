import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComandeFormComponent } from './comande-form.component';

describe('ComandeFormComponent', () => {
  let component: ComandeFormComponent;
  let fixture: ComponentFixture<ComandeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComandeFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComandeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
