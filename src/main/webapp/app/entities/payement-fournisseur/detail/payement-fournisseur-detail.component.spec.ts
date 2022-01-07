import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayementFournisseurDetailComponent } from './payement-fournisseur-detail.component';

describe('PayementFournisseur Management Detail Component', () => {
  let comp: PayementFournisseurDetailComponent;
  let fixture: ComponentFixture<PayementFournisseurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PayementFournisseurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ payementFournisseur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PayementFournisseurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PayementFournisseurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load payementFournisseur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.payementFournisseur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
