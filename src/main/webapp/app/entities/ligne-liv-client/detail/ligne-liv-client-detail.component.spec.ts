import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LigneLivClientDetailComponent } from './ligne-liv-client-detail.component';

describe('LigneLivClient Management Detail Component', () => {
  let comp: LigneLivClientDetailComponent;
  let fixture: ComponentFixture<LigneLivClientDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LigneLivClientDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ligneLivClient: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LigneLivClientDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LigneLivClientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ligneLivClient on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ligneLivClient).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
